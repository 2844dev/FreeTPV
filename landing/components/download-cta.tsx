import { Download, Github, FileCode } from "lucide-react";

export function DownloadCTA() {
  return (
    <section id="descargar" className="py-20 px-4 sm:px-6 lg:px-8 bg-muted/50">
      <div className="max-w-4xl mx-auto text-center">
        <h2 className="text-3xl sm:text-4xl font-bold text-foreground mb-4">
          Empieza a usar FreeTPV hoy
        </h2>
        <p className="text-lg text-muted-foreground mb-10 max-w-2xl mx-auto">
          Descarga la ultima version gratuita para Windows o accede al codigo 
          fuente en GitHub para compilarlo tu mismo.
        </p>

        <div className="flex flex-col sm:flex-row items-center justify-center gap-4 mb-8">
          <a
            href="https://github.com/2844dev/FreeTPV/releases"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex items-center gap-2 bg-primary text-primary-foreground px-8 py-4 rounded-xl font-semibold text-lg hover:bg-primary/90 transition-colors shadow-lg shadow-primary/25 w-full sm:w-auto justify-center"
          >
            <Download className="w-5 h-5" />
            Descargar para Windows
          </a>

          <a
            href="https://github.com/2844dev/FreeTPV"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex items-center gap-2 bg-secondary text-secondary-foreground px-8 py-4 rounded-xl font-semibold text-lg hover:bg-secondary/80 transition-colors border border-border w-full sm:w-auto justify-center"
          >
            <Github className="w-5 h-5" />
            Ver en GitHub
          </a>
        </div>

        <div className="flex flex-col sm:flex-row items-center justify-center gap-6 text-sm text-muted-foreground">
          <div className="flex items-center gap-2">
            <FileCode className="w-4 h-4" />
            <span>Licencia GPL-3.0</span>
          </div>
          <div className="flex items-center gap-2">
            <span>Requiere Java 21+</span>
          </div>
          <div className="flex items-center gap-2">
            <span>Windows 10/11</span>
          </div>
        </div>
      </div>
    </section>
  );
}
