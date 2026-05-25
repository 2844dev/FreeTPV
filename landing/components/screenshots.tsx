import { CreditCard, Monitor, Package, Settings } from "lucide-react";

const screenshots = [
  {
    id: "ventas",
    icon: Monitor,
    title: "Pantalla de ventas",
    description: "Categorías, productos, ticket y total en una sola vista.",
    filename: "screenshot-ventas.png",
  },
  {
    id: "cobro",
    icon: CreditCard,
    title: "Cobro y cambio",
    description: "Pago en efectivo o tarjeta con calculadora y vuelta.",
    filename: "screenshot-cobro.png",
  },
  {
    id: "productos",
    icon: Package,
    title: "Gestión de productos",
    description: "Tabla de productos, filtros, búsqueda y formulario de edición.",
    filename: "screenshot-productos.png",
  },
  {
    id: "ajustes",
    icon: Settings,
    title: "Ajustes del negocio",
    description: "Empresa, ticket, impresora, apariencia y copias de seguridad.",
    filename: "screenshot-ajustes.png",
  },
];

export function Screenshots() {
  return (
    <section id="capturas" className="scroll-mt-20 py-20 px-4 sm:px-6 lg:px-8">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-3xl sm:text-4xl font-bold text-foreground mb-4">
            Un vistazo a FreeTPV
          </h2>
          <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
            Las capturas mostrarán las partes principales de la aplicación,
            desde la venta diaria hasta la configuración del negocio.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {screenshots.map((screenshot) => (
            <div key={screenshot.id} className="group">
              <div className="aspect-video bg-muted rounded-xl border border-border overflow-hidden mb-4 flex items-center justify-center transition-colors group-hover:border-primary/40 group-hover:bg-primary/[0.03]">
                <div className="text-center p-8">
                  <div className="mx-auto mb-4 flex h-12 w-12 items-center justify-center rounded-lg bg-background border border-border group-hover:border-primary/30">
                    <screenshot.icon className="w-6 h-6 text-primary" />
                  </div>
                  <p className="text-sm font-medium text-foreground">
                    Captura pendiente
                  </p>
                  <p className="mt-1 text-xs text-muted-foreground">
                    {screenshot.filename}
                  </p>
                </div>
              </div>
              <h3 className="font-semibold text-foreground mb-1">{screenshot.title}</h3>
              <p className="text-sm text-muted-foreground">{screenshot.description}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
