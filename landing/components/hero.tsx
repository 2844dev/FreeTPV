import Image from "next/image";
import { Download, Github } from "lucide-react";

export function Hero() {
  return (
    <section className="pt-32 pb-20 px-4 sm:px-6 lg:px-8">
      <div className="max-w-4xl mx-auto text-center">
        <div className="flex justify-center mb-8">
          <Image
            src="/logo.png"
            alt="FreeTPV Logo"
            width={120}
            height={120}
            className="rounded-2xl shadow-lg"
            priority
          />
        </div>

        <h1 className="text-4xl sm:text-5xl lg:text-6xl font-bold text-foreground mb-6 text-balance">
          El TPV gratuito para tu negocio de hosteleria
        </h1>

        <p className="text-lg sm:text-xl text-muted-foreground mb-10 max-w-2xl mx-auto text-pretty">
          Un software TPV gratuito y de codigo abierto enfocado a la hosteleria. 
          Gestiona ventas, productos y usuarios de forma sencilla y profesional.
        </p>

        <div className="flex flex-col sm:flex-row items-center justify-center gap-4">
          <a
            href="https://github.com/2844dev/FreeTPV/releases"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex items-center gap-2 bg-primary text-primary-foreground px-8 py-4 rounded-xl font-semibold text-lg hover:bg-primary/90 transition-colors shadow-lg shadow-primary/25"
          >
            <Download className="w-5 h-5" />
            Descargar para Windows
          </a>

          <a
            href="https://github.com/2844dev/FreeTPV"
            target="_blank"
            rel="noopener noreferrer"
            className="inline-flex items-center gap-2 bg-secondary text-secondary-foreground px-8 py-4 rounded-xl font-semibold text-lg hover:bg-secondary/80 transition-colors border border-border"
          >
            <Github className="w-5 h-5" />
            Ver en GitHub
          </a>
        </div>

        <p className="mt-6 text-sm text-muted-foreground">
          Disponible para Windows. Requiere Java 21 o superior.
        </p>
      </div>
    </section>
  );
}
