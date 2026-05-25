import type { MetadataRoute } from "next";

export const dynamic = "force-static";

export default function sitemap(): MetadataRoute.Sitemap {
  return [
    {
      url: "https://freetpv.pages.dev/",
      lastModified: "2026-05-25",
      changeFrequency: "weekly",
      priority: 1,
    },
  ];
}
