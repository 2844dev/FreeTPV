import { CreditCard, Monitor, Package, Settings } from "lucide-react";

const screenshots = [
  {
    id: "ventas",
    icon: Monitor,
    title: "Pantalla de ventas",
    description: "Categorías, productos, ticket y total en una sola vista.",
    src: "/images/screenshots/screenshot-ventas.webp",
    alt: "Pantalla de ventas de FreeTPV con categorías, productos y ticket",
  },
  {
    id: "cobro",
    icon: CreditCard,
    title: "Cobro y cambio",
    description: "Pago en efectivo o tarjeta con calculadora y vuelta.",
    src: "/images/screenshots/screenshot-cobro.webp",
    alt: "Pantalla de cobro de FreeTPV con opciones de efectivo y tarjeta",
  },
  {
    id: "productos",
    icon: Package,
    title: "Gestión de productos",
    description: "Tabla de productos, filtros, búsqueda y formulario de edición.",
    src: "/images/screenshots/screenshot-productos.webp",
    alt: "Pantalla de gestión de productos de FreeTPV",
  },
  {
    id: "ajustes",
    icon: Settings,
    title: "Ajustes del negocio",
    description: "Empresa, ticket, impresora, apariencia y copias de seguridad.",
    src: "/images/screenshots/screenshot-ajustes.webp",
    alt: "Pantalla de ajustes de negocio de FreeTPV",
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
            Capturas reales de las partes principales de la aplicación,
            desde la venta diaria hasta la configuración del negocio.
          </p>
        </div>

        <div className="grid grid-cols-1 gap-8 md:grid-cols-2">
          {screenshots.map((screenshot) => (
            <div key={screenshot.id} className="group">
              <div className="mb-4 aspect-video overflow-hidden rounded-xl border border-border bg-muted shadow-sm transition-colors group-hover:border-primary/40">
                <img
                  src={screenshot.src}
                  alt={screenshot.alt}
                  loading="lazy"
                  className="h-full w-full object-cover transition-transform duration-300 group-hover:scale-[1.02]"
                />
              </div>
              <div className="mb-1 flex items-center gap-2">
                <screenshot.icon className="h-4 w-4 text-primary" aria-hidden="true" />
                <h3 className="font-semibold text-foreground">{screenshot.title}</h3>
              </div>
              <p className="text-sm text-muted-foreground">{screenshot.description}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
