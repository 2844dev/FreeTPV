import { Header } from "@/components/header";
import { Hero } from "@/components/hero";
import { TrustSignals } from "@/components/trust-signals";
import { Features } from "@/components/features";
import { Screenshots } from "@/components/screenshots";
import { FAQ } from "@/components/faq";
import { DownloadCTA } from "@/components/download-cta";
import { Footer } from "@/components/footer";

export default function Home() {
  return (
    <>
      <Header />
      <main>
        <Hero />
        <TrustSignals />
        <Features />
        <Screenshots />
        <FAQ />
        <DownloadCTA />
      </main>
      <Footer />
    </>
  );
}
