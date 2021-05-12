/*
 * Copyright (c) 2021. fanpoll All rights reserved.
 */

package fanpoll.club

import fanpoll.infra.ProjectAuthConfig
import fanpoll.infra.auth.*
import fanpoll.infra.openapi.ProjectOpenApi
import fanpoll.infra.openapi.schema.component.support.SecurityScheme
import io.ktor.auth.Authentication

object ClubAuth {

    const val serviceProviderName = "${ClubConst.projectId}-service"

    private val serviceAuthSchemes = listOf(ProjectOpenApi.apiKeySecurityScheme)

    private val sessionIdAuthScheme = SecurityScheme.apiKeyAuth("SessionIdAuth", SessionAuth.SESSION_ID_HEADER_NAME)

    private val userAuthSchemes = listOf(ProjectOpenApi.apiKeySecurityScheme, sessionIdAuthScheme)

    val allAuthSchemes = listOf(ProjectOpenApi.apiKeySecurityScheme, sessionIdAuthScheme)

    val Public = PrincipalAuth.Service.public(serviceProviderName, serviceAuthSchemes, ClubPrincipalSources.App)

    val User = PrincipalAuth.User(
        SessionAuthConfig.providerName, userAuthSchemes,
        mapOf(ClubUserType.User.value to ClubUserType.User.value.roles), ClubPrincipalSources.App
    )

    val Admin = PrincipalAuth.User(
        SessionAuthConfig.providerName, userAuthSchemes,
        mapOf(ClubUserType.User.value to setOf(ClubUserRole.Admin.value)), ClubPrincipalSources.App
    )

    val Member = PrincipalAuth.User(
        SessionAuthConfig.providerName, userAuthSchemes,
        mapOf(ClubUserType.User.value to setOf(ClubUserRole.Member.value)), ClubPrincipalSources.App
    )

}

data class ClubAuthConfig(
    val session: SessionConfig,
    val android: UserAuthConfig,
    val iOS: UserAuthConfig
) : ProjectAuthConfig {

    init {
        android.session = session
        iOS.session = session
    }

    override fun getPrincipalSourceAuthConfigs(): List<PrincipalSourceAuthConfig<ServiceAuthApiKeyCredential>> = listOf(android, iOS)
}

fun Authentication.Configuration.club(authConfig: ClubAuthConfig) {

    serviceApiKey(ClubAuth.serviceProviderName, authConfig.getPrincipalSourceAuthConfigs())
}