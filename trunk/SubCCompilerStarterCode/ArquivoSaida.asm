extern _printf
SECTION .data
	intFormat: db "%d", 10, 0
	doubleFormat: db "%.2f", 10, 0
	a: dd 0
	b: dq 0.0
SECTION .text
	global _WinMain@16
