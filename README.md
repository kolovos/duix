Duix is a Java-based dual-screen PDF viewer, mainly targeted at Beamer presentations with `\setbeameroption{show notes on second screen}`, but it also works with regular PDF presentations. Duix was inspired by [dspdfviewer](https://github.com/dannyedel/dspdfviewer) and makes use of [Apache PDFBox](https://pdfbox.apache.org/) for PDF rendering.

Keyboard shortcuts
---
* <kbd>&rightarrow;</kbd>, <kbd>&downarrow;</kbd>, <kbd>space</kbd>: Next slide
* <kbd>&leftarrow;</kbd>, <kbd>&uparrow;</kbd>, <kbd>backspace</kbd>: Previous slide
* <kbd>B</kbd>: Blank/unblank screen
* <kbd>esc</kbd>: Exit

Minimal Beamer presentation with notes on second screen
---
```tex
\documentclass{beamer}
\usepackage{pgfpages}

\setbeameroption{show notes on second screen}

\title{Duix}
\author{Dimitris Kolovos}
\institute{University of York, UK}
\date{\today}

\begin{document}

\begin{frame}
	\titlepage
\end{frame}

\section{Introduction}

\begin{frame}
	\frametitle{About Duix}
	\begin{itemize}
		\item Dual-screen Java-based PDF viewer
		\item Works with Beamer presentations
		\item Uses Apache PDFBox for PDF rendering
	\end{itemize}
	\note{
		Duix was inspired by dspdfviewer, 
		a dual-screen PDF viewer written in C++
	}
\end{frame}

\end{document}
```
Download
---
You can download the latest version of Duix from http://tinyurl.com/duixapp. If you are on Mac OS X, you can download `Duix.dmg`; for all other operating systems you can download `duix.jar` (executable jar).
