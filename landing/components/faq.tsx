"use client";

import type { ReactNode } from "react";
import { useState } from "react";

const faqs: { question: string; answer: ReactNode }[] = [
  {
    question: "¿FreeTPV es gratis?",
    answer: (
      <>
        Sí. No hay planes de pago, cuotas mensuales ni funciones bloqueadas. El código se
        publica bajo licencia MIT para que puedas revisarlo o adaptarlo.
      </>
    ),
  },
  {
    question: "¿Para qué negocios está pensado?",
    answer: (
      <>
        Para bares, cafeterías, restaurantes pequeños y negocios de hostelería que necesitan
        vender rápido, gestionar productos y sacar tickets sin depender de un sistema complejo.
      </>
    ),
  },
  {
    question: "¿Funciona sin conexión a internet?",
    answer: (
      <>
        Sí. FreeTPV es una aplicación de escritorio: los productos, usuarios y ajustes se guardan
        en una base de datos local en el equipo donde se instala.
      </>
    ),
  },
  {
    question: "¿Qué sistema operativo soporta?",
    answer: (
      <>
        La versión principal está pensada para Windows 10 y Windows 11. El instalador incluye
        Java, así que no necesitas instalarlo aparte para usar la aplicación.
      </>
    ),
  },
  {
    question: "¿Necesito configurar algo para imprimir tickets?",
    answer: (
      <>
        Normalmente sí. Debes tener instalada la impresora térmica en Windows y seleccionarla en
        los ajustes de FreeTPV. La impresión está pensada para tickets ESC/POS.
      </>
    ),
  },
  {
    question: "¿Puedo tener varios usuarios?",
    answer: (
      <>
        Sí. Puedes crear usuarios para el equipo y separar el acceso por roles, por ejemplo
        administrador y camarero.
      </>
    ),
  },
  {
    question: "¿Permite gestionar productos, ventas y copias de seguridad?",
    answer: (
      <>
        Sí. Incluye productos y categorías, pantalla de ventas, cobro en efectivo o tarjeta,
        ajustes del negocio y copias de seguridad de los datos.
      </>
    ),
  },
  {
    question: "¿Tiene virus?",
    answer: (
      <>
        No. Las descargas oficiales se publican desde{" "}
        <a
          href="https://github.com/2844dev/FreeTPV/releases/latest"
          target="_blank"
          rel="noopener noreferrer"
          className="font-medium text-primary underline underline-offset-4 transition-colors hover:text-primary/80"
        >
          GitHub Releases
        </a>
        , y el código es público para que cualquiera pueda revisarlo.
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
            Respuestas prácticas sobre instalación, impresión, usuarios y funcionamiento local.
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
