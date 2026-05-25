import { Download, FileCode } from "lucide-react";

import { TiltLink } from "@/components/tilt-link";

function GithubIcon({ className }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
      <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z" />
    </svg>
  );
}

const ownerSteps = [
  "Descarga la última versión para Windows.",
  "Instala FreeTPV en el equipo del negocio.",
  "Configura productos, usuarios e impresora.",
  "Empieza a vender desde la pantalla principal.",
];

const developerSteps = [
  "Haz un fork del repositorio en GitHub.",
  "Clona tu fork y crea una rama para tus cambios.",
  "Prueba tus mejoras en local antes de subirlas.",
  "Abre una pull request para proponer los cambios.",
];

export function DownloadCTA() {
  return (
    <section id="descargar" className="scroll-mt-20 bg-background px-4 py-20 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-6xl">
        <div className="mx-auto mb-12 max-w-3xl text-center">
          <h2 className="mb-4 text-3xl font-bold text-foreground sm:text-4xl">
            Empieza a usar FreeTPV hoy
          </h2>
          <p className="text-lg text-muted-foreground">
            Descárgalo para tu negocio o participa en el desarrollo desde GitHub.
          </p>
        </div>

        <div className="grid gap-6 lg:grid-cols-2">
          <div className="rounded-2xl border border-primary/20 bg-card p-6 shadow-sm transition-colors hover:border-primary/35 hover:bg-primary/[0.03] sm:p-8">
            <div className="mb-5 flex h-12 w-12 items-center justify-center rounded-xl bg-primary/10">
              <Download className="h-6 w-6 text-primary" />
            </div>

            <h3 className="mb-3 text-2xl font-semibold text-foreground">
              Si tienes un negocio de hostelería
            </h3>
            <p className="mb-6 text-sm leading-6 text-muted-foreground">
              Instala FreeTPV en Windows y prepara tu bar, restaurante o cafetería para
              gestionar ventas, productos y usuarios sin cuotas.
            </p>

            <ol className="mb-8 space-y-3 text-sm text-muted-foreground">
              {ownerSteps.map((step, index) => (
                <li key={step} className="flex gap-3">
                  <span className="flex h-6 w-6 shrink-0 items-center justify-center rounded-full bg-primary/10 text-xs font-semibold text-primary">
                    {index + 1}
                  </span>
                  <span>{step}</span>
                </li>
              ))}
            </ol>

            <TiltLink
              href="https://github.com/2844dev/FreeTPV/releases"
              target="_blank"
              rel="noopener noreferrer"
              className="inline-flex h-12 w-full items-center justify-center gap-2 rounded-lg bg-primary px-6 text-base font-medium text-primary-foreground transition-colors hover:bg-primary/90 sm:w-auto"
            >
              <Download className="h-5 w-5" />
              Descargar para Windows
            </TiltLink>
          </div>

          <div className="rounded-2xl border border-border bg-card p-6 shadow-sm transition-colors hover:border-primary/30 hover:bg-primary/[0.03] sm:p-8">
            <div className="mb-5 flex h-12 w-12 items-center justify-center rounded-xl bg-muted">
              <FileCode className="h-6 w-6 text-primary" />
            </div>

            <h3 className="mb-3 text-2xl font-semibold text-foreground">
              Si eres desarrollador
            </h3>
            <p className="mb-6 text-sm leading-6 text-muted-foreground">
              Revisa el código, adapta el proyecto a tus necesidades o propón mejoras
              mediante pull requests.
            </p>

            <ol className="mb-8 space-y-3 text-sm text-muted-foreground">
              {developerSteps.map((step, index) => (
                <li key={step} className="flex gap-3">
                  <span className="flex h-6 w-6 shrink-0 items-center justify-center rounded-full bg-muted text-xs font-semibold text-foreground">
                    {index + 1}
                  </span>
                  <span>{step}</span>
                </li>
              ))}
            </ol>

            <TiltLink
              href="https://github.com/2844dev/FreeTPV"
              target="_blank"
              rel="noopener noreferrer"
              className="inline-flex h-12 w-full items-center justify-center gap-2 rounded-lg border border-border bg-background px-6 text-base font-medium text-foreground transition-colors hover:bg-secondary sm:w-auto"
            >
              <GithubIcon className="h-5 w-5" />
              Ver código en GitHub
            </TiltLink>
          </div>
        </div>
      </div>
    </section>
  );
}
