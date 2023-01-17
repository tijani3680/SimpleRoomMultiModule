package core.views.base

interface BaseState<A : BaseAction, M : BaseMutation> {
    fun isActionRegistered(action: A): Boolean = true
    fun reduce(mutation: M): BaseState<A, M> = this
}
