data class User(
    var uid: String? = null,
    var email: String? = null,
    var name: String? = null
) {
    constructor() : this("", "","")
}
