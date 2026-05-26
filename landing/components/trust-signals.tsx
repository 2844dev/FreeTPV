const signals = [
  {
    title: "Gratis y sin cuotas",
    description: "Sin pagos mensuales ni funciones bloqueadas.",
  },
  {
    title: "Funciona en local",
    description: "Tus datos se guardan en el equipo del negocio.",
  },
  {
    title: "Código abierto MIT",
    description: "Proyecto público en GitHub, revisable y adaptable.",
  },
];

export function TrustSignals() {
  return (
    <section className="border-b border-border bg-background px-4 py-6 sm:px-6 lg:px-8">
      <div className="mx-auto max-w-6xl">
        <ul className="grid gap-5 border-y border-border py-5 sm:grid-cols-3 sm:gap-8">
          {signals.map((signal) => (
            <li key={signal.title}>
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
