"use client";

import type { ReactNode } from "react";
import { useState } from "react";

const faqs: { question: string; answer: ReactNode }[] = [
  {
    question: "¿FreeTPV es gratis?",
    answer: <>Sí. FreeTPV es gratuito y de código abierto bajo licencia MIT.</>,
  },
  {
    question: "¿Para qué negocios está pensado?",
    answer: (
      <>
        Está pensado para bares, restaurantes, cafeterías y pequeños negocios de hostelería
        que necesitan un TPV sencillo y sin cuotas.
      </>
    ),
  },
  {
    question: "¿Funciona sin conexión a internet?",
    answer: (
      <>
        Sí. FreeTPV es una aplicación de escritorio y guarda los datos localmente en el equipo
        donde se instala.
      </>
    ),
  },
  {
    question: "¿Qué sistema operativo soporta?",
    answer: <>La versión principal está pensada para Windows 10 y Windows 11.</>,
  },
  {
    question: "¿Permite gestionar productos, usuarios y ventas?",
    answer: (
      <>
        Sí. Incluye gestión de productos, categorías, usuarios, permisos, ventas, cobros y
        copias de seguridad.
      </>
    ),
  },
  {
    question: "¿Tiene virus?",
    answer: (
      <>
        No. FreeTPV es código abierto, por lo que cualquiera puede revisar el código. Las
        descargas oficiales se publican desde{" "}
        <a
          href="https://github.com/2844dev/FreeTPV/releases"
          target="_blank"
          rel="noopener noreferrer"
          className="font-medium text-primary underline underline-offset-4 transition-colors hover:text-primary/80"
        >
          GitHub Releases
        </a>
        .
      </>
    ),
  },
];

export function FAQ() {
  const [openItems, setOpenItems] = useState<Set<number>>(() => new Set([0]));

  function toggleItem(index: number) {
    setOpenItems((currentItems) => {
      const nextItems = new Set(currentItems);

      if (nextItems.has(index)) {
        nextItems.delete(index);
      } else {
        nextItems.add(index);
      }

      return nextItems;
    });
  }

  return (
    <section id="faq" className="scroll-mt-20 border-y border-border bg-muted/50 px-4 py-20 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-3xl">
        <div className="mb-10 text-center">
          <h2 className="mb-3 text-3xl font-bold text-foreground sm:text-4xl">
            Preguntas frecuentes
          </h2>
          <p className="mx-auto max-w-xl text-base text-muted-foreground">
            Respuestas rápidas sobre el funcionamiento, la licencia y la instalación de FreeTPV.
          </p>
        </div>

        <div className="overflow-hidden rounded-xl border border-border bg-card shadow-sm">
          {faqs.map((faq, index) => {
            const isOpen = openItems.has(index);
            const answerId = `faq-answer-${index}`;

            return (
              <div key={faq.question} className="faq-item border-b border-border last:border-b-0">
                <button
                  type="button"
                  className="faq-trigger flex w-full items-center justify-between gap-4 px-5 py-4 text-left font-semibold text-foreground"
                  aria-expanded={isOpen}
                  aria-controls={answerId}
                  onClick={() => toggleItem(index)}
                >
                  <span>{faq.question}</span>
                  <span className="faq-toggle" aria-hidden="true">
                    <span />
                    <span />
                  </span>
                </button>

                <div id={answerId} className="faq-answer" data-open={isOpen}>
                  <div className="px-5 pb-4 pt-0 text-sm leading-6 text-muted-foreground">
                    {faq.answer}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </section>
  );
}
