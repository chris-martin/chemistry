# Chemistry

## Example usage

```
import chemistry.dsl._
```

Atomic mass of fructose (C<sub>6</sub>H<sub>12</sub>O<sub>6</sub>):

```
val fructoseMass =
  atomicMass("C") * 6 +
  atomicMass("H") * 12 +
  atomicMass("O") * 6
```

Moles per gram of fructose:

```
val fructoseMolPerGram = avogadro / fructoseMass
```
