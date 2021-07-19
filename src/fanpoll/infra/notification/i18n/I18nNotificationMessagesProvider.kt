/*
 * Copyright (c) 2021. fanpoll All rights reserved.
 */

package fanpoll.infra.notification.i18n

import fanpoll.infra.base.i18n.Lang
import fanpoll.infra.base.i18n.MessagesProvider

class I18nNotificationMessagesProvider(messagesProvider: MessagesProvider<*>) : MessagesProvider<I18nNotificationMessages> {

    override val messages: Map<Lang, I18nNotificationMessages> = messagesProvider.messages
        .mapValues { I18nNotificationMessages(it.value) }

}