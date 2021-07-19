/*
 * Copyright (c) 2021. fanpoll All rights reserved.
 */

package fanpoll.infra.notification.channel

import fanpoll.infra.logging.writers.LogWriter
import fanpoll.infra.notification.NotificationLogConfig
import fanpoll.infra.notification.NotificationMessage
import fanpoll.infra.notification.logging.NotificationMessageLog
import mu.KotlinLogging
import java.time.Instant

private fun toMockLog(message: NotificationMessage): NotificationMessageLog {
    return message.toNotificationMessageLog().apply {
        sendAt = Instant.now()
        success = true
        content = message.content.toJson().toString()
        rspCode = "mock"
    }
}

class MockNotificationChannelSender(
    private val loggingConfig: NotificationLogConfig,
    private val logWriter: LogWriter
) : NotificationChannelSender {

    private val logger = KotlinLogging.logger {}

    override val maxReceiversPerRequest: Int = 1

    override fun send(message: NotificationMessage) {
        logger.debug { "sendNotification: ${message.debugString()}" }
        logger.debug { "content: ${message.content.toJson()}" }

        if (loggingConfig.enabled)
            logWriter.write(toMockLog(message))
    }
}