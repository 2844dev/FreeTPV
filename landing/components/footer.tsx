function GithubIcon({ className }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
      <path d="M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z" />
    </svg>
  );
}

function DiscordIcon({ className }: { className?: string }) {
  return (
    <svg className={className} viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
      <path d="M20.32 4.37A19.8 19.8 0 0 0 15.36 3a13.7 13.7 0 0 0-.64 1.32 18.6 18.6 0 0 0-5.44 0A13.7 13.7 0 0 0 8.64 3c-1.74.3-3.4.77-4.96 1.37C.54 9.07-.31 13.66.11 18.18A20 20 0 0 0 6.2 21a14.8 14.8 0 0 0 1.3-2.1c-.72-.27-1.4-.6-2.04-.98l.5-.39A14.2 14.2 0 0 0 12 19.08c2.12 0 4.15-.48 6.04-1.55l.5.39c-.64.39-1.33.72-2.04.98.38.74.82 1.45 1.3 2.1a20 20 0 0 0 6.09-2.82c.5-5.24-.84-9.79-3.57-13.81ZM8.02 15.4c-1.18 0-2.14-1.08-2.14-2.4s.94-2.4 2.14-2.4c1.2 0 2.16 1.08 2.14 2.4 0 1.32-.94 2.4-2.14 2.4Zm7.96 0c-1.18 0-2.14-1.08-2.14-2.4s.94-2.4 2.14-2.4c1.2 0 2.16 1.08 2.14 2.4 0 1.32-.94 2.4-2.14 2.4Z" />
    </svg>
  );
}

export function Footer() {
  return (
    <footer className="relative overflow-hidden border-t border-border bg-background px-4 py-12 sm:px-6 lg:px-8">
      <div aria-hidden="true" className="pointer-events-none absolute inset-x-0 bottom-0 h-44">
        <svg
          className="absolute inset-x-0 bottom-0 h-full w-full text-primary/16"
          viewBox="0 0 1440 180"
          preserveAspectRatio="none"
        >
          <path
            fill="currentColor"
            d="M0,78 C160,10 300,132 480,78 C660,24 800,10 980,52 C1160,94 1280,114 1440,58 L1440,180 L0,180 Z"
          />
        </svg>
        <div className="absolute inset-x-0 bottom-0 h-full bg-white/35 backdrop-blur-md" />
      </div>

      <div className="relative z-10 mx-auto max-w-6xl">
        <div className="flex flex-col items-center justify-between gap-6 md:flex-row">
          <div className="flex items-center gap-3">
            <img
              src="/favicon.ico"
              alt="Logo de FreeTPV"
              className="h-10 w-10 rounded-xl"
            />
            <div>
              <p className="font-semibold text-foreground">FreeTPV</p>
              <p className="text-sm text-muted-foreground">TPV gratuito para hostelería</p>
            </div>
          </div>

          <div className="flex flex-wrap items-center justify-center gap-x-6 gap-y-3 text-sm text-muted-foreground">
            <a
              href="https://github.com/2844dev/FreeTPV"
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center gap-2 transition-colors hover:text-primary"
            >
              <GithubIcon className="h-4 w-4" />
              GitHub
            </a>
            <a
              href="https://github.com/2844dev/FreeTPV/issues"
              target="_blank"
              rel="noopener noreferrer"
              className="transition-colors hover:text-primary"
            >
              Reportar bug
            </a>
            <a
              href="https://github.com/2844dev/FreeTPV/blob/main/LICENSE"
              target="_blank"
              rel="noopener noreferrer"
              className="transition-colors hover:text-primary"
            >
              Licencia MIT
            </a>
          </div>
        </div>

        <div className="mt-7 border-t border-border pt-6 text-center text-sm text-muted-foreground">
          <p>
            Desarrollado por{" "}
            <a
              href="https://github.com/2844dev"
              target="_blank"
              rel="noopener noreferrer"
              className="font-medium text-primary underline underline-offset-4"
            >
              2844dev
            </a>
          </p>
          <p className="mt-2 inline-flex items-center justify-center gap-2">
            <DiscordIcon className="h-4 w-4" />
            <span>Discord:</span>
            <span className="font-medium text-foreground">@2844</span>
          </p>
        </div>
      </div>
    </footer>
  );
}
