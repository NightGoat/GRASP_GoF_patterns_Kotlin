package gof.factory_method

interface Stove {
    fun cook(): Dish
}

class PieStove: Stove {
    override fun cook(): Dish {
        return Dish.ApplePie()
    }
}

class SoupStove: Stove {
    override fun cook(): Dish {
        return Dish.ChickenSoup()
    }
}

class CandyStove: Stove {
    override fun cook(): Dish {
        return Dish.Candy()
    }
}
