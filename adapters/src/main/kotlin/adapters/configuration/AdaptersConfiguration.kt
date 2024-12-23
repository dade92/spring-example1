package adapters.configuration

import adapters.JdbcOrdersRepository
import adapters.JdbcUrlBuilder
import adapters.RestProductsRepository
import adapters.users.JdbcUserRepository
import domain.NowDateTimeProvider
import domain.OrdersRepository
import domain.ProductsRepository
import domain.UserRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.client.RestTemplate
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(DbSettings::class, ProductsProperties::class)
class AdaptersConfiguration {

    @Bean
    fun ordersRepository(appJdbcTemplate: JdbcTemplate): OrdersRepository = JdbcOrdersRepository(
        appJdbcTemplate,
        NowDateTimeProvider()
    )

    @Bean
    fun productsRepository(
        productsProperties: ProductsProperties
    ): ProductsRepository =
        RestProductsRepository(productsProperties.url, RestTemplate())

    @Bean
    fun userRepository(appJdbcTemplate: JdbcTemplate): UserRepository =
        JdbcUserRepository(appJdbcTemplate)

    @Bean
    fun appDataSource(dbSettings: DbSettings): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.url(JdbcUrlBuilder.build(dbSettings.host, dbSettings.database, dbSettings.port))
        dataSourceBuilder.username(dbSettings.username)
        dataSourceBuilder.password(dbSettings.password)
        return dataSourceBuilder.build()
    }

    @Bean
    fun appJdbcTemplate(appDataSource: DataSource): JdbcTemplate? = JdbcTemplate(appDataSource)

}