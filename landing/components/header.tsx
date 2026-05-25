"use client";

import Image from "next/image";
import Link from "next/link";
import { Github, Menu, X } from "lucide-react";
import { useState } from "react";

export function Header() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <header className="fixed top-0 left-0 right-0 z-50 bg-background/80 backdrop-blur-md border-b border-border">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <Link href="/" className="flex items-center gap-3">
            <Image
              src="/logo.png"
              alt="FreeTPV Logo"
              width={40}
              height={40}
              className="rounded-lg"
            />
            <span className="font-semibold text-lg text-foreground">FreeTPV</span>
          </Link>

          <nav className="hidden md:flex items-center gap-8">
            <a href="#caracteristicas" className="text-muted-foreground hover:text-foreground transition-colors">
              Caracteristicas
            </a>
            <a href="#capturas" className="text-muted-foreground hover:text-foreground transition-colors">
              Capturas
            </a>
            <a href="#descargar" className="text-muted-foreground hover:text-foreground transition-colors">
              Descargar
            </a>
            <a
              href="https://github.com/2844dev/FreeTPV"
              target="_blank"
              rel="noopener noreferrer"
              className="flex items-center gap-2 text-muted-foreground hover:text-foreground transition-colors"
            >
              <Github className="w-5 h-5" />
              GitHub
            </a>
          </nav>

          <button
            className="md:hidden p-2 text-muted-foreground hover:text-foreground"
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            aria-label="Toggle menu"
          >
            {mobileMenuOpen ? <X className="w-6 h-6" /> : <Menu className="w-6 h-6" />}
          </button>
        </div>

        {mobileMenuOpen && (
          <nav className="md:hidden py-4 border-t border-border">
            <div className="flex flex-col gap-4">
              <a
                href="#caracteristicas"
                className="text-muted-foreground hover:text-foreground transition-colors"
                onClick={() => setMobileMenuOpen(false)}
              >
                Caracteristicas
              </a>
              <a
                href="#capturas"
                className="text-muted-foreground hover:text-foreground transition-colors"
                onClick={() => setMobileMenuOpen(false)}
              >
                Capturas
              </a>
              <a
                href="#descargar"
                className="text-muted-foreground hover:text-foreground transition-colors"
                onClick={() => setMobileMenuOpen(false)}
              >
                Descargar
              </a>
              <a
                href="https://github.com/2844dev/FreeTPV"
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center gap-2 text-muted-foreground hover:text-foreground transition-colors"
              >
                <Github className="w-5 h-5" />
                GitHub
              </a>
            </div>
          </nav>
        )}
      </div>
    </header>
  );
}
