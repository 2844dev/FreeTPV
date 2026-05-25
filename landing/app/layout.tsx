import type { Metadata, Viewport } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import { CursorGlow } from "@/components/cursor-glow";
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
  title: "FreeTPV - Software TPV Gratuito para Hostelería",
  description:
    "Un software TPV gratuito y de código abierto enfocado a la hostelería. Gestiona tu bar, restaurante o cafetería de forma sencilla y sin costes de licencia.",
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
  openGraph: {
    title: "FreeTPV - Software TPV Gratuito para Hostelería",
    description:
      "Un software TPV gratuito y de código abierto enfocado a la hostelería.",
    type: "website",
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
    <body className="bg-background font-sans antialiased">
    <CursorGlow />
    <div className="relative z-10">
      {children}
    </div>
    </body>
    </html>
  );
}
