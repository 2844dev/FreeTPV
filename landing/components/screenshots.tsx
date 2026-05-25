import { ImageIcon } from "lucide-react";

const screenshots = [
  {
    id: "login",
    title: "Pantalla de inicio de sesion",
    description: "Acceso seguro con sistema de usuarios",
    filename: "screenshot-login.png",
  },
  {
    id: "ventas",
    title: "Pantalla de ventas",
    description: "Interfaz principal para gestionar pedidos",
    filename: "screenshot-ventas.png",
  },
  {
    id: "productos",
    title: "Gestion de productos",
    description: "Organiza productos y categorias",
    filename: "screenshot-productos.png",
  },
  {
    id: "cobro",
    title: "Pantalla de cobro",
    description: "Proceso de pago rapido y sencillo",
    filename: "screenshot-cobro.png",
  },
];

export function Screenshots() {
  return (
    <section id="capturas" className="py-20 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-3xl sm:text-4xl font-bold text-foreground mb-4">
            Conoce la interfaz
          </h2>
          <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
            Una interfaz moderna y facil de usar, disenada para agilizar 
            el trabajo diario en tu establecimiento.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {screenshots.map((screenshot) => (
            <div key={screenshot.id} className="group">
              {/* 
                PLACEHOLDER: Coloca aqui la captura de pantalla
                
                Archivo esperado: /public/screenshots/{screenshot.filename}
                Ejemplo: /public/screenshots/screenshot-login.png
                
                Formato recomendado: PNG
                Tamaño recomendado: 1280x800px o 1920x1080px
                Aspecto: 16:10 o 16:9
              */}
              <div className="aspect-video bg-muted rounded-xl border border-border overflow-hidden mb-4 flex items-center justify-center group-hover:border-primary/50 transition-colors">
                <div className="text-center p-8">
                  <ImageIcon className="w-12 h-12 text-muted-foreground/50 mx-auto mb-4" />
                  <p className="text-sm text-muted-foreground mb-2">
                    Placeholder para captura
                  </p>
                  <code className="text-xs bg-muted-foreground/10 px-2 py-1 rounded">
                    /public/screenshots/{screenshot.filename}
                  </code>
                </div>
              </div>
              <h3 className="font-semibold text-foreground mb-1">{screenshot.title}</h3>
              <p className="text-sm text-muted-foreground">{screenshot.description}</p>
            </div>
          ))}
        </div>

        {/* Instrucciones para el desarrollador */}
        <div className="mt-12 p-6 bg-muted/50 rounded-xl border border-border">
          <h4 className="font-semibold text-foreground mb-3">Instrucciones para agregar capturas:</h4>
          <ol className="text-sm text-muted-foreground space-y-2 list-decimal list-inside">
            <li>Crea la carpeta <code className="bg-muted-foreground/10 px-1 rounded">/public/screenshots/</code></li>
            <li>Guarda las capturas con los siguientes nombres:</li>
          </ol>
          <ul className="text-sm text-muted-foreground mt-2 ml-6 space-y-1">
            {screenshots.map((s) => (
              <li key={s.id}>
                <code className="bg-muted-foreground/10 px-1 rounded">{s.filename}</code> - {s.title}
              </li>
            ))}
          </ul>
          <p className="text-sm text-muted-foreground mt-3">
            Formato: PNG | Tamaño recomendado: 1280x800px o 1920x1080px
          </p>
        </div>
      </div>
    </section>
  );
}
