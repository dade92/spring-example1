package adapters

object JdbcUrlBuilder {

    fun build(
        host: String,
        database: String,
        port: String,
    ): String = "jdbc:mysql://$host:$port/$database?serverTimezone=UTC"

}