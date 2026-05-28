"use client";

import {
  ChevronLeft,
  ChevronRight,
  CreditCard,
  Maximize2,
  Monitor,
  Package,
  Settings,
  X,
} from "lucide-react";
import { useEffect, useState } from "react";

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
  const [activeIndex, setActiveIndex] = useState<number | null>(null);
  const activeScreenshot = activeIndex === null ? null : screenshots[activeIndex];

  function closeCarousel() {
    setActiveIndex(null);
  }

  function showPrevious() {
    setActiveIndex((current) => {
      if (current === null) return current;
      return current === 0 ? screenshots.length - 1 : current - 1;
    });
  }

  function showNext() {
    setActiveIndex((current) => {
      if (current === null) return current;
      return current === screenshots.length - 1 ? 0 : current + 1;
    });
  }

  useEffect(() => {
    if (activeIndex === null) return;

    const previousOverflow = document.body.style.overflow;
    document.body.style.overflow = "hidden";

    function handleKeyDown(event: KeyboardEvent) {
      if (event.key === "Escape") closeCarousel();
      if (event.key === "ArrowLeft") showPrevious();
      if (event.key === "ArrowRight") showNext();
    }

    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.body.style.overflow = previousOverflow;
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, [activeIndex]);

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
          {screenshots.map((screenshot, index) => (
            <button
              key={screenshot.id}
              type="button"
              className="group block text-left"
              onClick={() => setActiveIndex(index)}
              aria-label={`Abrir captura: ${screenshot.title}`}
            >
              <div className="relative mb-4 aspect-video overflow-hidden rounded-xl border border-border bg-muted p-2 shadow-sm transition-colors group-hover:border-primary/40 group-focus-visible:outline-none group-focus-visible:ring-2 group-focus-visible:ring-primary/50">
                <img
                  src={screenshot.src}
                  alt={screenshot.alt}
                  loading="lazy"
                  className="h-full w-full rounded-lg object-contain"
                />
                <div className="absolute right-4 top-4 rounded-full border border-border bg-background/90 p-2 text-foreground opacity-0 shadow-sm transition-opacity group-hover:opacity-100 group-focus-visible:opacity-100">
                  <Maximize2 className="h-4 w-4" aria-hidden="true" />
                </div>
              </div>
              <div className="mb-1 flex items-center gap-2">
                <screenshot.icon className="h-4 w-4 text-primary" aria-hidden="true" />
                <h3 className="font-semibold text-foreground">{screenshot.title}</h3>
              </div>
              <p className="text-sm text-muted-foreground">{screenshot.description}</p>
            </button>
          ))}
        </div>
      </div>

      {activeScreenshot && (
        <div
          className="fixed inset-0 z-[100] flex items-center justify-center bg-background/95 p-4 backdrop-blur-sm sm:p-6"
          role="dialog"
          aria-modal="true"
          aria-label="Carrusel de capturas"
          onClick={closeCarousel}
        >
          <div className="relative flex h-full w-full max-w-7xl flex-col" onClick={(event) => event.stopPropagation()}>
            <div className="mb-4 flex items-center justify-between gap-4">
              <div>
                <p className="text-sm text-muted-foreground">
                  {activeIndex + 1} / {screenshots.length}
                </p>
                <h3 className="text-xl font-semibold text-foreground">{activeScreenshot.title}</h3>
              </div>
              <button
                type="button"
                className="rounded-full border border-border bg-background p-2 text-foreground shadow-sm transition-colors hover:bg-muted focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary/50"
                onClick={closeCarousel}
                aria-label="Cerrar carrusel"
              >
                <X className="h-5 w-5" aria-hidden="true" />
              </button>
            </div>

            <div className="relative min-h-0 flex-1 rounded-2xl border border-border bg-muted p-2 shadow-lg sm:p-4">
              <img
                src={activeScreenshot.src}
                alt={activeScreenshot.alt}
                className="h-full w-full rounded-xl object-contain"
              />

              <button
                type="button"
                className="absolute left-3 top-1/2 -translate-y-1/2 rounded-full border border-border bg-background/90 p-2 text-foreground shadow-sm transition-colors hover:bg-muted focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary/50 sm:left-6"
                onClick={showPrevious}
                aria-label="Ver captura anterior"
              >
                <ChevronLeft className="h-6 w-6" aria-hidden="true" />
              </button>

              <button
                type="button"
                className="absolute right-3 top-1/2 -translate-y-1/2 rounded-full border border-border bg-background/90 p-2 text-foreground shadow-sm transition-colors hover:bg-muted focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary/50 sm:right-6"
                onClick={showNext}
                aria-label="Ver captura siguiente"
              >
                <ChevronRight className="h-6 w-6" aria-hidden="true" />
              </button>
            </div>

            <div className="mt-4 grid grid-cols-4 gap-2 sm:gap-3">
              {screenshots.map((screenshot, index) => (
                <button
                  key={screenshot.id}
                  type="button"
                  className={`aspect-video rounded-lg border bg-muted p-1 transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary/50 ${
                    activeIndex === index ? "border-primary" : "border-border hover:border-primary/40"
                  }`}
                  onClick={() => setActiveIndex(index)}
                  aria-label={`Ver captura: ${screenshot.title}`}
                >
                  <img
                    src={screenshot.src}
                    alt=""
                    className="h-full w-full rounded-md object-contain"
                    aria-hidden="true"
                  />
                </button>
              ))}
            </div>
          </div>
        </div>
      )}
    </section>
  );
}
