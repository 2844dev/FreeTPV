"use client";

import type { AnchorHTMLAttributes, MouseEvent, ReactNode } from "react";

type TiltLinkProps = AnchorHTMLAttributes<HTMLAnchorElement> & {
  children: ReactNode;
};

export function TiltLink({ children, className = "", ...props }: TiltLinkProps) {
  function handleMouseMove(event: MouseEvent<HTMLAnchorElement>) {
    const element = event.currentTarget;
    const rect = element.getBoundingClientRect();

    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    const rotateX = ((rect.height / 2 - y) / rect.height) * 12;
    const rotateY = ((x - rect.width / 2) / rect.width) * 12;

    element.style.setProperty("--rotate-x", `${rotateX}deg`);
    element.style.setProperty("--rotate-y", `${rotateY}deg`);
  }

  function handleMouseLeave(event: MouseEvent<HTMLAnchorElement>) {
    const element = event.currentTarget;

    element.style.setProperty("--rotate-x", "0deg");
    element.style.setProperty("--rotate-y", "0deg");
  }

  return (
    <a
      {...props}
      className={`tilt-link ${className}`}
      onMouseMove={handleMouseMove}
      onMouseLeave={handleMouseLeave}
    >
      {children}
    </a>
  );
}
