const faqs = [
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
          className="font-medium text-primary underline underline-offset-4 hover:text-primary/80"
        >
          GitHub Releases
        </a>
        .
      </>
    ),
  },
];

export function FAQ() {
  return (
    <section id="faq" className="scroll-mt-20 py-20 px-4 sm:px-6 lg:px-8 bg-background">
      <div className="max-w-3xl mx-auto">
        <div className="text-center mb-10">
          <h2 className="text-3xl sm:text-4xl font-bold text-foreground mb-3">
            Preguntas frecuentes
          </h2>
          <p className="text-base text-muted-foreground max-w-xl mx-auto">
            Respuestas rápidas sobre el funcionamiento, la licencia y la instalación de FreeTPV.
          </p>
        </div>

        <div className="divide-y divide-border rounded-xl border border-border bg-card">
          {faqs.map((faq, index) => (
            <details key={faq.question} className="group" open={index === 0}>
              <summary className="flex cursor-pointer list-none items-center justify-between gap-4 px-5 py-4 text-left font-semibold text-foreground transition-colors hover:text-primary [&::-webkit-details-marker]:hidden">
                <span>{faq.question}</span>
                <span className="text-xl leading-none text-muted-foreground transition-transform group-open:rotate-180">
                  ˅
                </span>
              </summary>
              <div className="px-5 pb-4 pt-0 text-sm leading-6 text-muted-foreground">
                {faq.answer}
              </div>
            </details>
          ))}
        </div>
      </div>
    </section>
  );
}
