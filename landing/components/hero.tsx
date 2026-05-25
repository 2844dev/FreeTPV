import { Download } from "lucide-react";

import { TiltLink } from "@/components/tilt-link";

function GithubIcon({ className }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
      <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z" />
    </svg>
  );
}

export function Hero() {
  return (
    <section className="relative overflow-hidden border-b border-border px-4 pt-28 pb-20 sm:px-6 lg:px-8">
      <div className="relative z-10 mx-auto max-w-4xl text-center">
        <h1 className="animate-hero-in mx-auto max-w-3xl text-4xl font-semibold tracking-tight text-foreground sm:text-5xl lg:text-6xl">
          TPV gratuito para pequeños negocios de hostelería.
        </h1>

        <p className="animate-hero-in animate-delay-100 mx-auto mt-6 max-w-2xl text-lg leading-8 text-muted-foreground">
          FreeTPV es una aplicación de escritorio para gestionar ventas,
          productos y usuarios en bares, restaurantes y cafeterías. Código
          abierto, sin cuotas y pensado para instalarse rápido.
        </p>

        <div className="animate-hero-in animate-delay-200 mt-10 flex flex-col items-center justify-center gap-3 sm:flex-row">
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
            <GithubIcon className="h-5 w-5" />
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
