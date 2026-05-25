import Image from "next/image";
import { Github } from "lucide-react";

export function Footer() {
  return (
    <footer className="py-12 px-4 sm:px-6 lg:px-8 border-t border-border">
      <div className="max-w-6xl mx-auto">
        <div className="flex flex-col md:flex-row items-center justify-between gap-6">
          <div className="flex items-center gap-3">
            <Image
              src="/logo.png"
              alt="FreeTPV Logo"
              width={32}
              height={32}
              className="rounded-lg"
            />
            <span className="font-semibold text-foreground">FreeTPV</span>
          </div>

          <div className="flex items-center gap-6 text-sm text-muted-foreground">
            <a
              href="https://github.com/2844dev/FreeTPV"
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center gap-2 hover:text-foreground transition-colors"
            >
              <Github className="w-4 h-4" />
              GitHub
            </a>
            <a
              href="https://github.com/2844dev/FreeTPV/blob/main/LICENSE"
              target="_blank"
              rel="noopener noreferrer"
              className="hover:text-foreground transition-colors"
            >
              Licencia GPL-3.0
            </a>
          </div>
        </div>

        <div className="mt-8 pt-8 border-t border-border text-center text-sm text-muted-foreground">
          <p>
            Desarrollado por{" "}
            <a
              href="https://github.com/2844dev"
              target="_blank"
              rel="noopener noreferrer"
              className="text-primary hover:underline"
            >
              2844dev
            </a>
          </p>
        </div>
      </div>
    </footer>
  );
}
