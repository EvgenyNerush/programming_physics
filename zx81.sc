// Lehmer generator from Sinclair ZX81
def zx81Rand(seed: Long): LazyList[Long] =
  seed #:: zx81Rand( 75 * seed % 65537 )

def period(x: Long, n: Int, xs: LazyList[Long]): Int = xs match {
  case LazyList() => 0
  case y #:: ys =>
    if (y == x)
      n + 1
    else period(x, n + 1, ys)
}

assert( period(1, 0, LazyList(5,4,3,2,1,0)) == 5 )

val rands = zx81Rand(43)
period(rands.head, 0, rands.tail)

def zx81Doubles(seed: Long): LazyList[Double] =
  zx81Rand(seed) map (_.toDouble / 65536 * 2 - 1)

val n = 10000
val m = 100
(1 to m)
  .map (zx81Doubles(_).take(n).sum)
  .map (_.abs)
  .sum / m

