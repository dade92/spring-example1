package domain

class DefaultSaveUserUseCase(
    private val userRepository: UserRepository
) : SaveUserUseCase {

    override fun save(user: User): Boolean = userRepository.addUser(user).isPresent

}