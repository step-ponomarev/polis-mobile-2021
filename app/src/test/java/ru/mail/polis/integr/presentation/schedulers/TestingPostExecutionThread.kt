package ru.mail.polis.integr.presentation.schedulers

import ru.mail.polis.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestingPostExecutionThread : PostExecutionThread {
    override val scheduler: Scheduler = Schedulers.trampoline()
}
