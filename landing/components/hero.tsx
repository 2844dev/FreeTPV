import Image from "next/image";
import { Download, Github } from "lucide-react";

import { TiltLink } from "@/components/tilt-link";

export function Hero() {
  return (
    <section className="border-b border-border bg-background px-4 pt-20 pb-20 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-4xl text-center">
        <div className="animate-hero-in mb-6 flex justify-center">
          <Image
            src="/logo.png"
            alt="Logo de FreeTPV"
            width={360}
            height={203}
            className="h-auto w-64 sm:w-72 md:w-80"
            priority
            loading="eager"
          />
        </div>

        <h1 className="animate-hero-in animate-delay-100 mx-auto max-w-3xl text-4xl font-semibold tracking-tight text-foreground sm:text-5xl lg:text-6xl">
          TPV gratuito para pequeños negocios de hostelería.
        </h1>

        <p className="animate-hero-in animate-delay-200 mx-auto mt-6 max-w-2xl text-lg leading-8 text-muted-foreground">
          FreeTPV es una aplicación de escritorio para gestionar ventas,
          productos y usuarios en bares, restaurantes y cafeterías. Código
          abierto, sin cuotas y pensado para instalarse rápido.
        </p>

        <div className="animate-hero-in animate-delay-300 mt-10 flex flex-col items-center justify-center gap-3 sm:flex-row">
          <TiltLink
            href="https://github.com/2844dev/FreeTPV/releases"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex h-12 w-full items-center justify-center gap-2 rounded-lg bg-primary px-6 text-base font-medium text-primary-foreground transition-colors hover:bg-primary/90 sm:w-auto"
          >
            <Download className="h-5 w-5" />
            Descargar para Windows
          </TiltLink>

          <TiltLink
            href="https://github.com/2844dev/FreeTPV"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex h-12 w-full items-center justify-center gap-2 rounded-lg border border-border bg-background px-6 text-base font-medium text-foreground transition-colors hover:bg-secondary sm:w-auto"
          >
            <Github className="h-5 w-5" />
            Ver en GitHub
          </TiltLink>
        </div>

        <p className="animate-hero-in animate-delay-300 mt-5 text-sm text-muted-foreground">
          MIT · Windows 10/11 · Java 25
        </p>
      </div>
    </section>
  );
}
