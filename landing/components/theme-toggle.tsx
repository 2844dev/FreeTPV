"use client";

import { Moon, Sun } from "lucide-react";
import { useEffect, useState } from "react";

const STORAGE_KEY = "freetpv-theme";

type Theme = "latte" | "mocha";

function getSystemTheme(): Theme {
  return window.matchMedia("(prefers-color-scheme: dark)").matches ? "mocha" : "latte";
}

function getSavedTheme(): Theme | null {
  const savedTheme = window.localStorage.getItem(STORAGE_KEY);

  if (savedTheme === "latte" || savedTheme === "mocha") {
    return savedTheme;
  }

  return null;
}

function applyTheme(theme: Theme) {
  document.documentElement.dataset.theme = theme;
}

export function ThemeToggle() {
  const [theme, setTheme] = useState<Theme>("latte");
  const isDark = theme === "mocha";

  useEffect(() => {
    const mediaQuery = window.matchMedia("(prefers-color-scheme: dark)");
    const savedTheme = getSavedTheme();
    const initialTheme = savedTheme ?? getSystemTheme();

    setTheme(initialTheme);
    applyTheme(initialTheme);

    function handleSystemThemeChange() {
      if (getSavedTheme() !== null) {
        return;
      }

      const nextTheme = mediaQuery.matches ? "mocha" : "latte";
      setTheme(nextTheme);
      applyTheme(nextTheme);
    }

    mediaQuery.addEventListener("change", handleSystemThemeChange);

    return () => {
      mediaQuery.removeEventListener("change", handleSystemThemeChange);
    };
  }, []);

  function toggleTheme() {
    const nextTheme = isDark ? "latte" : "mocha";

    setTheme(nextTheme);
    applyTheme(nextTheme);
    window.localStorage.setItem(STORAGE_KEY, nextTheme);
  }

  return (
    <button
      type="button"
      onClick={toggleTheme}
      className="inline-flex h-9 w-9 items-center justify-center rounded-lg border border-border bg-background text-muted-foreground transition-colors hover:border-primary/40 hover:bg-secondary hover:text-foreground"
      aria-label={isDark ? "Activar modo claro" : "Activar modo oscuro"}
      title={isDark ? "Activar modo claro" : "Activar modo oscuro"}
    >
      {isDark ? <Sun className="h-4 w-4" /> : <Moon className="h-4 w-4" />}
    </button>
  );
}
