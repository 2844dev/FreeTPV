import Image from "next/image";
import { Download } from "lucide-react";

import { TiltLink } from "@/components/tilt-link";

function GithubIcon({ className }: { className?: string }) {
  return (
    <svg
      className={className}
      width="24"
      height="24"
      viewBox="0 0 24 24"
      fill="currentColor"
      aria-hidden="true"
    >
      <path d="M12 0C5.37 0 0 5.37 0 12c0 5.3 3.44 9.8 8.21 11.39.6.11.79-.26.79-.58v-2.23c-3.34.73-4.03-1.42-4.03-1.42-.55-1.39-1.33-1.76-1.33-1.76-1.09-.74.08-.73.08-.73 1.2.08 1.84 1.24 1.84 1.24 1.07 1.83 2.81 1.3 3.49 1 .11-.78.42-1.3.76-1.6-2.66-.31-5.46-1.33-5.46-5.93 0-1.31.47-2.38 1.24-3.22-.12-.3-.54-1.52.12-3.18 0 0 1.01-.32 3.3 1.23.96-.27 1.98-.4 3-.4s2.05.13 3 .4c2.29-1.55 3.3-1.23 3.3-1.23.65 1.65.24 2.87.12 3.18.77.84 1.24 1.91 1.24 3.22 0 4.61-2.81 5.62-5.48 5.92.43.37.82 1.1.82 2.22v3.29c0 .32.19.69.8.58A12.01 12.01 0 0 0 24 12c0-6.63-5.37-12-12-12z" />
    </svg>
  );
}

export function Hero() {
  return (
    <section id="top" className="border-b border-border bg-background px-4 pt-24 pb-20 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-4xl text-center">
        <div className="animate-hero-in mb-6 flex justify-center">
          <Image
            src="/images/logo.webp"
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
            href="https://github.com/2844dev/FreeTPV/releases/latest"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex h-12 w-full items-center justify-center gap-2 rounded-lg bg-primary px-6 text-base font-medium text-primary-foreground transition-colors hover:bg-primary/90 sm:w-auto"
          >
            <Download className="h-5 w-5" />
            Descargar última versión para Windows
          </TiltLink>

          <TiltLink
            href="https://github.com/2844dev/FreeTPV"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex h-12 w-full items-center justify-center gap-2 rounded-lg border border-border bg-background px-6 text-base font-medium text-foreground transition-colors hover:bg-secondary sm:w-auto"
          >
            <GithubIcon className="h-5 w-5" />
            Ver en GitHub
          </TiltLink>
        </div>

        <p className="animate-hero-in animate-delay-300 mt-5 text-sm text-muted-foreground">
          MIT · Windows 10/11 · Java 25 incluido en el instalador
        </p>
      </div>
    </section>
  );
}
