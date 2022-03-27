/*
 * Copyright (c) 2021. fanpoll All rights reserved.
 */

package fanpoll.infra.logging.writers

import fanpoll.infra.logging.LogEntity

interface LogWriter {

    fun write(logEntity: LogEntity)

    fun shutdown() {}
}