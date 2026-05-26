const signals = [
  {
    title: "Gratis y sin cuotas",
    description: "Sin suscripciones, pagos mensuales ni funciones bloqueadas por pago.",
  },
  {
    title: "Funciona en local",
    description: "Los datos se guardan en el equipo del negocio y no necesitas internet para vender.",
  },
  {
    title: "Código abierto MIT",
    description: "El proyecto es público en GitHub y se puede revisar, adaptar o mejorar.",
  },
];

export function TrustSignals() {
  return (
    <section className="border-b border-border bg-background px-4 py-8 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-6xl">
        <ul className="grid border-y border-border md:grid-cols-3 md:border-y-0 md:border-l">
          {signals.map((signal) => (
            <li
              key={signal.title}
              className="border-b border-border py-5 last:border-b-0 md:border-b-0 md:border-r md:px-6 md:first:pl-0 md:last:pr-0"
            >
              <p className="text-sm font-semibold text-foreground">{signal.title}</p>
              <p className="mt-1 text-sm leading-6 text-muted-foreground">
                {signal.description}
              </p>
            </li>
          ))}
        </ul>
      </div>
    </section>
  );
}
