object Solution {
    def maxArea(height: Array[Int]): Int = {
        val n = height.length
        var i = 0
        var j = n - 1
        var volume = Math.min(height(i), height(j)) * (j - i)
        while ( i != j ) {
            if (height(i) <= height(j)) {
                i += 1
            } else {
                j -= 1
            }
            val candidateVolume = Math.min(height(i), height(j)) * (j - i)
            if (candidateVolume >= volume)
              volume = candidateVolume
        }
        volume
    }
}
