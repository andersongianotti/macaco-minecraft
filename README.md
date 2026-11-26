# Macaco Vermelho

Mod para **Minecraft Java Edition 26.3** (Fabric) que adiciona o **Macaco Vermelho**, um pet domesticável.

## O que faz

- Macaco vermelho com barriga e focinho claros
- Domesticável com **bagas doces**, **maçã** ou **cenoura** (1 em cada 3 itens)
- Segue o dono, senta com **Shift + botão direito**, teleporta até você se ficar longe
- Cura ao alimentar depois de domesticado
- Spawna naturalmente em biomas de selva
- Animação de andar, balançar de rabo e sons de papagaio

## Build

```
gradlew.bat build
```

Jar gerado em `build/libs/macaco-1.0.0.jar`.

O CI do GitHub Actions compila o jar a cada push e publica em **Releases**. Para instalar a versão mais recente direto na pasta mods:

```powershell
powershell -ExecutionPolicy Bypass -File install-latest.ps1
```
