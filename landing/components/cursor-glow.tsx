"use client";

import { useEffect, useRef } from "react";

export function CursorGlow() {
    const glowRef = useRef<HTMLDivElement>(null);
    const target = useRef({ x: 0, y: 0 });
    const current = useRef({ x: 0, y: 0 });
    const frame = useRef<number | null>(null);

    useEffect(() => {
        const glow = glowRef.current;

        if (!glow) {
            return;
        }

        current.current.x = window.innerWidth / 2;
        current.current.y = window.innerHeight / 2;
        target.current.x = window.innerWidth / 2;
        target.current.y = window.innerHeight / 2;

        const handlePointerMove = (event: PointerEvent) => {
            target.current.x = event.clientX;
            target.current.y = event.clientY;
            glow.style.opacity = "1";
        };

        const handlePointerLeave = () => {
            glow.style.opacity = "0";
        };

        const animate = () => {
            current.current.x += (target.current.x - current.current.x) * 0.22;
            current.current.y += (target.current.y - current.current.y) * 0.22;

            glow.style.transform = `translate3d(${current.current.x}px, ${current.current.y}px, 0) translate(-50%, -50%)`;

            frame.current = requestAnimationFrame(animate);
        };

        window.addEventListener("pointermove", handlePointerMove);
        document.addEventListener("mouseleave", handlePointerLeave);

        frame.current = requestAnimationFrame(animate);

        return () => {
            window.removeEventListener("pointermove", handlePointerMove);
            document.removeEventListener("mouseleave", handlePointerLeave);

            if (frame.current) {
                cancelAnimationFrame(frame.current);
            }
        };
    }, []);

    return <div ref={glowRef} className="cursor-glow" aria-hidden="true" />;
}