package com.mateo.freetpv.controller;

import atlantafx.base.theme.Styles;
import atlantafx.base.theme.Tweaks;
import com.mateo.freetpv.dao.UsuarioDAO;
import com.mateo.freetpv.model.Usuario;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;


/**
 * Clase controller, muestra información al usuario y se comunica con {@link UsuarioDAO}
 * para crear objetos {@link Usuario} o editarlos y guardarlos en la BD
 *
 * @author Mateo
 * @since  08/03/2026
 */
public class EmpleadosController {
    // Panel principal de empleados
    @FXML private BorderPane empleadosPane;
    @FXML private CheckBox filtroCheckBox;
    @FXML private Button nuevoButton;
    @FXML private TextField buscarField;
    @FXML private TableView<Usuario> empleadosTable;
    @FXML private TableColumn<Usuario, Integer> codigoColumn;
    @FXML private TableColumn<Usuario, String> nombreColumn;
    @FXML private TableColumn<Usuario, String> rolColumn;
    @FXML private TableColumn<Usuario, String> fechaColumn;
    @FXML private TableColumn<Usuario, Void> estadoColumn;
    @FXML private TableColumn<Usuario, Void> editarColumn;

    // Panel extra de edicion y creacion de empleados
    @FXML private BorderPane nuevoempleadoPane;
    @FXML private TextField usuarioField;
    @FXML private PasswordField pinField;
    @FXML private ChoiceBox<String> rolChoiceBox;
    @FXML private CheckBox estadoCheckBox;
    @FXML private Button guardarButton;
    @FXML private Button cancelarButton;
    @FXML private Label errorLabel;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    private Usuario usuarioEditando = null;

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

    /**
     *
     * Inicializa la pantalla de gestión de empleados
     *
     */
    @FXML public void initialize() {

        // Nos aseguramos que el panel de nuevo empleado esta invisible
        nuevoempleadoPane.setVisible(false);

        // Nos aseguramos que no tiene texto
        errorLabel.setText("");

        // Indicamos a cada columna que atributo de usuario mostrar
        codigoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        rolColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_creacion"));

        estadoColumn.getStyleClass().add(Tweaks.ALIGN_CENTER);
        estadoColumn.setCellFactory(param -> new TableCell<>() {
            private FontIcon icon = new FontIcon();

            @Override protected void updateItem(Void item, boolean empty) {
                if (empty) {
                    setGraphic(null);
                } else {
                    icon.getStyleClass().removeAll(Styles.SUCCESS, Styles.DANGER);
                    if (getTableView().getItems().get(getIndex()).getEstado()) {
                        icon.setIconLiteral("fas-user-check");
                        icon.getStyleClass().add(Styles.SUCCESS);
                    } else {
                        icon.setIconLiteral("fas-user-minus");
                        icon.getStyleClass().add(Styles.DANGER);
                    }
                    setGraphic(icon);
                }
            }
        });

        // Columna editar
        editarColumn.getStyleClass().add(Tweaks.ALIGN_CENTER);

        // Configuramos fabrica para poner los botones en cada celda
        editarColumn.setCellFactory(param -> new TableCell<>() {

            // Creamos un atributo boton
            private Button editarButton = new Button("");
            // Bloque inicializador
            {
                // Configuramos como se vera el boton
                FontIcon icon = new FontIcon();
                icon.setIconLiteral("fas-edit");
                editarButton.setGraphic(icon);
                editarButton.setStyle("-fx-padding: 4;");
                editarButton.setOnAction(e -> {
                    mostrarFormularioEditar(getTableView().getItems().get(getIndex()));
                });
            }
            // Llamamos a updateItem para decir si se vera o no el botón
            @Override protected void updateItem(Void item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editarButton);
                    }
            }
        });

        // Actualizamos la tabla con los usuarios
        cargarUsuarios();

        // Añadimos los roles al choicebox para elegir
        rolChoiceBox.setItems(FXCollections.observableArrayList("Admin", "Camarero"));
    }

    // Guardamos el usuario

    /**
     *
     * Dependiendo de si se esta editando un usuario o creando uno,
     * edita o crea un usuario
     *
     */
    @FXML public void guardarFormulario() {
        String nombre = usuarioField.getText();
        String rol = rolChoiceBox.getSelectionModel().getSelectedItem();
        String pin = pinField.getText();
        boolean estado = estadoCheckBox.isSelected();

        // Comprobamos que tenga pin un usuario nuevo
        if (usuarioEditando == null && pin.isEmpty()) {
            errorLabel.setText("Debe insertar un pin");
            return;
        }

        // Comprobamos que todos los usuarios tengan nombre y rol
        if (nombre.isEmpty() || rol == null) {
            errorLabel.setText("Debe poner un usuario y rol");
            return;
        }

        // Comprobamos que el nombre tenga menos de 35 caracteres
        if (nombre.length() > 35) {
            errorLabel.setText("El nombre debe de tener menos de 35 caracteres.");
            return;
        }

        // Comprobamos que no exista un usuario el mismo nombre
        if (usuarioDAO.existeUsuario(nombre) && (usuarioEditando == null || !nombre.equals(usuarioEditando.getNombre()))) {
            errorLabel.setText("Ya existe un usuario con ese nombre");
            return;
        }
        // Creamos un usuario
        if (usuarioEditando == null) {
            usuarioDAO.crearUsuario(nombre, pin, rol);
            infoAlert.setTitle("Crear Usuario");
            infoAlert.setHeaderText("Usuario creado correctamente");
            infoAlert.setContentText("Usuario " + usuarioField.getText() + " creado.");
            infoAlert.showAndWait();
        // Editamos un usuario
        } else {
            usuarioDAO.editarUsuario(usuarioEditando, nombre, pin, rol, estado);
            infoAlert.setTitle("Editar Usuario");
            infoAlert.setHeaderText("Usuario editado correctamente");
            infoAlert.setContentText("Usuario " + usuarioField.getText() + " editado.");
            infoAlert.showAndWait();
        }
        cargarUsuarios();
        cerrarFormulario();
    }

    // Mostramos el formulario vacio si le damos al boton de Nuevo Usuario
    @FXML public void mostrarFormularioNuevo() {
        empleadosPane.setDisable(true);
        nuevoempleadoPane.setVisible(true);

        // Forzamos que un usuario este habilitado por defecto
        estadoCheckBox.setSelected(true);
        estadoCheckBox.setDisable(true);
    }

    public void mostrarFormularioEditar(Usuario usuario) {

        // Escogemos el usuario editado como el seleccionado en la tabla
        usuarioEditando = usuario;

        // Comprobamos que haya un usuario seleccionado
        if (usuarioEditando != null) {
            // Ponemos los datos del usuario en el formulario
            usuarioField.setText(usuarioEditando.getNombre());

            // Deseleccionamos el campo despues de renderizarlo
            Platform.runLater(() -> guardarButton.requestFocus());

            // Dejamos el pin en blanco si no lo queremos editar
            pinField.clear();

            // Seleccionamos admin si tiene admin, si no siempre sera camarero
            if (usuarioEditando.getRol().equals("Admin")) {
                rolChoiceBox.getSelectionModel().select(0); // Admin es la opcion 0
            } else {
                rolChoiceBox.getSelectionModel().select(1); // Camarero es la opcion 1
            }

            // Habilitamos la edición del estado y lo seleccionamos acorde a su estado
            estadoCheckBox.setDisable(false);
            estadoCheckBox.setSelected(usuarioEditando.getEstado());

            // Hacemos visible el panel
            empleadosPane.setDisable(true);
            nuevoempleadoPane.setVisible(true);

        }
    }

    // Eliminamos todos los datos que queden en los campos del formulario una vez se cancela
    @FXML public void cerrarFormulario() {
        nuevoempleadoPane.setVisible(false);

        // Limpiamos el formulario
        usuarioField.clear();
        pinField.clear();
        rolChoiceBox.getSelectionModel().clearSelection();
        errorLabel.setText("");

        // Reestablecemos el usuario editado
        usuarioEditando = null;

        // Habilitamos el panel principal
        empleadosPane.setDisable(false);
    }

    @FXML public void cargarUsuarios() {
        // Creamos una lista de usuarios llamando al metodo de UsuarioDAO
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios(!filtroCheckBox.isSelected());

        if (usuarios != null && !usuarios.isEmpty()) {
            // Convertimos la lista a una observableList y lo ponemos como los items en la tabla
            empleadosTable.setItems(FXCollections.observableList(usuarios));
        }
    }
}
