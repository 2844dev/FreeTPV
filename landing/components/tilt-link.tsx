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

        const centerX = rect.width / 2;
        const centerY = rect.height / 2;

        const rotateX = ((centerY - y) / centerY) * 6;
        const rotateY = ((x - centerX) / centerX) * 6;

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