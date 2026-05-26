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
    <section id="capturas" className="px-4 py-20 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-6xl">
        <div className="mb-16 text-center">
          <h2 className="mb-4 text-3xl font-bold text-foreground sm:text-4xl">
            Un vistazo a FreeTPV
          </h2>
          <p className="mx-auto max-w-2xl text-lg text-muted-foreground">
            Las capturas mostrarán las partes principales de la aplicación,
            desde la venta diaria hasta la configuración del negocio.
          </p>
        </div>

        <div className="grid grid-cols-1 gap-8 md:grid-cols-2">
          {screenshots.map((screenshot) => (
            <div key={screenshot.id} className="group">
              <div className="mb-4 flex aspect-video items-center justify-center overflow-hidden rounded-xl border border-border bg-muted transition-colors group-hover:border-primary/40 group-hover:bg-primary/[0.03]">
                <div className="p-8 text-center">
                  <div className="mx-auto mb-4 flex h-12 w-12 items-center justify-center rounded-lg border border-border bg-background group-hover:border-primary/30">
                    <screenshot.icon className="h-6 w-6 text-primary" />
                  </div>
                  <p className="text-sm font-medium text-foreground">
                    Captura pendiente
                  </p>
                  <p className="mt-1 text-xs text-muted-foreground">
                    {screenshot.filename}
                  </p>
                </div>
              </div>
              <h3 className="mb-1 font-semibold text-foreground">{screenshot.title}</h3>
              <p className="text-sm text-muted-foreground">{screenshot.description}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
