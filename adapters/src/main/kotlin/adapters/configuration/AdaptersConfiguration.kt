package adapters.configuration

import adapters.JdbcOrdersRepository
import adapters.JdbcUserRepository
import domain.NowDateTimeProvider
import domain.OrdersRepository
import domain.UserRepository
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(DbSettings::class)
class AdaptersConfiguration {

    @Bean
    fun ordersRepository(appJdbcTemplate: JdbcTemplate): OrdersRepository = JdbcOrdersRepository(
        appJdbcTemplate,
        NowDateTimeProvider()
    )

    @Bean
    fun userRepository(appJdbcTemplate: JdbcTemplate): UserRepository {
        return JdbcUserRepository(appJdbcTemplate)
    }

    @Bean
    fun appDataSource(dbSettings: DbSettings): DataSource {
        val dataSourceBuilder = DataSourceBuilder.create()
        dataSourceBuilder.url(dbSettings.url)
        dataSourceBuilder.username(dbSettings.username)
        dataSourceBuilder.password(dbSettings.password)
        return dataSourceBuilder.build()
    }

    @Bean
    fun appJdbcTemplate(appDataSource: DataSource): JdbcTemplate? {
        return JdbcTemplate(appDataSource)
    }

}