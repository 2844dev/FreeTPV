import {
  Users,
  Package,
  Monitor,
  CreditCard,
  Printer,
  Palette,
  Database,
  Code2,
} from "lucide-react";

const features = [
  {
    icon: Users,
    title: "Sistema de usuarios",
    description:
      "Gestiona diferentes usuarios con permisos y roles personalizados para tu equipo.",
  },
  {
    icon: Package,
    title: "Gestion de productos",
    description:
      "Organiza tus productos por categorias con precios, descripciones e imagenes.",
  },
  {
    icon: Monitor,
    title: "Pantalla de ventas",
    description:
      "Interfaz intuitiva y rapida para gestionar pedidos y ventas en tiempo real.",
  },
  {
    icon: CreditCard,
    title: "Cobro flexible",
    description:
      "Acepta pagos en efectivo o tarjeta con gestion automatica del cambio.",
  },
  {
    icon: Printer,
    title: "Impresion ESC/POS",
    description:
      "Compatible con impresoras termicas ESC/POS para tickets profesionales.",
  },
  {
    icon: Palette,
    title: "Multiples temas",
    description:
      "Personaliza la apariencia con diferentes temas visuales incluidos.",
  },
  {
    icon: Database,
    title: "Copias de seguridad",
    description:
      "Sistema integrado de backup para proteger los datos de tu negocio.",
  },
  {
    icon: Code2,
    title: "Codigo abierto",
    description:
      "Software libre bajo licencia GPL-3.0. Modifica y adapta segun tus necesidades.",
  },
];

export function Features() {
  return (
    <section id="caracteristicas" className="py-20 px-4 sm:px-6 lg:px-8 bg-muted/50">
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-16">
          <h2 className="text-3xl sm:text-4xl font-bold text-foreground mb-4">
            Todo lo que necesitas para tu negocio
          </h2>
          <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
            FreeTPV incluye todas las herramientas esenciales para gestionar 
            tu bar, restaurante o cafeteria de forma profesional.
          </p>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {features.map((feature) => (
            <div
              key={feature.title}
              className="bg-card p-6 rounded-xl border border-border hover:border-primary/50 transition-colors"
            >
              <div className="w-12 h-12 bg-primary/10 rounded-lg flex items-center justify-center mb-4">
                <feature.icon className="w-6 h-6 text-primary" />
              </div>
              <h3 className="font-semibold text-foreground mb-2">{feature.title}</h3>
              <p className="text-sm text-muted-foreground">{feature.description}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
