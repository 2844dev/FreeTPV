"use client";

import type { CSSProperties } from "react";
import { useEffect } from "react";
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
    title: "Gestión de productos",
    description:
      "Organiza tus productos por categorías con precios, descripciones e imágenes.",
  },
  {
    icon: Monitor,
    title: "Pantalla de ventas",
    description:
      "Interfaz intuitiva y rápida para gestionar pedidos y ventas en tiempo real.",
  },
  {
    icon: CreditCard,
    title: "Cobro flexible",
    description:
      "Acepta pagos en efectivo o tarjeta con gestión automática del cambio.",
  },
  {
    icon: Printer,
    title: "Impresión ESC/POS",
    description:
      "Compatible con impresoras térmicas ESC/POS para tickets profesionales.",
  },
  {
    icon: Palette,
    title: "Múltiples temas",
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
    title: "Código abierto",
    description:
      "Software de código abierto bajo licencia MIT. Modifica y adapta el proyecto según tus necesidades.",
  },
];

export function Features() {
  useEffect(() => {
    const cards = document.querySelectorAll<HTMLElement>(".feature-card-reveal");

    if (!("IntersectionObserver" in window)) {
      cards.forEach((card) => card.classList.add("is-visible"));
      return;
    }

    const observer = new IntersectionObserver(
      (entries) => {
        entries.forEach((entry) => {
          if (entry.isIntersecting) {
            entry.target.classList.add("is-visible");
            observer.unobserve(entry.target);
          }
        });
      },
      {
        rootMargin: "0px 0px -80px 0px",
        threshold: 0.18,
      }
    );

    cards.forEach((card) => observer.observe(card));

    return () => observer.disconnect();
  }, []);

  return (
    <section id="caracteristicas" className="bg-muted/50 px-4 py-20 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-6xl">
        <div className="mb-16 text-center">
          <h2 className="mb-4 text-3xl font-bold text-foreground sm:text-4xl">
            Todo lo que necesitas para tu negocio
          </h2>
          <p className="mx-auto max-w-2xl text-lg text-muted-foreground">
            FreeTPV incluye las herramientas esenciales para gestionar
            tu bar, restaurante o cafetería de forma profesional.
          </p>
        </div>

        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
          {features.map((feature, index) => (
            <div
              key={feature.title}
              className="feature-card feature-card-reveal rounded-xl border border-border bg-card p-6"
              style={{ "--feature-delay": `${index * 80}ms` } as CSSProperties}
            >
              <div className="feature-icon mb-4 flex h-12 w-12 items-center justify-center rounded-lg bg-primary/10">
                <feature.icon className="h-6 w-6 text-primary" />
              </div>
              <h3 className="mb-2 font-semibold text-foreground">{feature.title}</h3>
              <p className="text-sm text-muted-foreground">{feature.description}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
