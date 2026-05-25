const faqs = [
  {
    question: "¿FreeTPV es gratis?",
    answer:
      "Sí. FreeTPV es gratuito y de código abierto bajo licencia MIT.",
  },
  {
    question: "¿Para qué negocios está pensado?",
    answer:
      "Está pensado para bares, restaurantes, cafeterías y pequeños negocios de hostelería que necesitan un TPV sencillo y sin cuotas.",
  },
  {
    question: "¿Funciona sin conexión a internet?",
    answer:
      "Sí. FreeTPV es una aplicación de escritorio y guarda los datos localmente en el equipo donde se instala.",
  },
  {
    question: "¿Qué sistema operativo soporta?",
    answer:
      "La versión principal está pensada para Windows 10 y Windows 11.",
  },
  {
    question: "¿Permite gestionar productos, usuarios y ventas?",
    answer:
      "Sí. Incluye gestión de productos, categorías, usuarios, permisos, ventas, cobros y copias de seguridad.",
  },
  {
    question: "¿Tiene virus?",
    answer:
      "No. FreeTPV es código abierto, por lo que cualquiera puede revisar el código. Las descargas oficiales se publican desde GitHub Releases.",
  },
];

export function FAQ() {
  return (
    <section id="faq" className="scroll-mt-20 py-20 px-4 sm:px-6 lg:px-8 bg-background">
      <div className="max-w-4xl mx-auto">
        <div className="text-center mb-14">
          <h2 className="text-3xl sm:text-4xl font-bold text-foreground mb-4">
            Preguntas frecuentes
          </h2>
          <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
            Respuestas rápidas sobre el funcionamiento, la licencia y la instalación de FreeTPV.
          </p>
        </div>

        <div className="divide-y divide-border rounded-xl border border-border bg-card">
          {faqs.map((faq) => (
            <div key={faq.question} className="p-6">
              <h3 className="font-semibold text-foreground mb-2">{faq.question}</h3>
              <p className="text-sm leading-6 text-muted-foreground">{faq.answer}</p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
