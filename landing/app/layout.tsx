import type { Metadata, Viewport } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  metadataBase: new URL("https://freetpv.pages.dev"),
  title: "FreeTPV - Software TPV Gratuito para Hostelería",
  description:
    "Software TPV gratuito y de código abierto para bares, restaurantes y cafeterías. Gestiona ventas, productos y usuarios de forma sencilla y sin cuotas.",
  keywords: [
    "TPV",
    "punto de venta",
    "hostelería",
    "restaurante",
    "bar",
    "cafetería",
    "software gratuito",
    "open source",
    "gestión",
  ],
  authors: [{ name: "FreeTPV" }],
  creator: "2844dev",
  alternates: {
    canonical: "/",
  },
  openGraph: {
    title: "FreeTPV - Software TPV Gratuito para Hostelería",
    description:
      "TPV gratuito y de código abierto para bares, restaurantes y cafeterías.",
    url: "/",
    siteName: "FreeTPV",
    locale: "es_ES",
    type: "website",
  },
  twitter: {
    card: "summary_large_image",
    title: "FreeTPV - Software TPV Gratuito para Hostelería",
    description:
      "TPV gratuito y de código abierto para bares, restaurantes y cafeterías.",
  },
};

export const viewport: Viewport = {
  themeColor: "#7c3aed",
  width: "device-width",
  initialScale: 1,
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="es" className={`${geistSans.variable} ${geistMono.variable}`}>
      <body className="bg-background font-sans antialiased">{children}</body>
    </html>
  );
}
