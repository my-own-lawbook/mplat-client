package me.bumiller.mol.domain.base

/**
 * Base class for any usecase in the domain module.
 */
fun interface BaseUsecase<in Input, out Output> {

    /**
     * Takes the input and creates it into an output.
     *
     * @param input The input
     * @return The created output
     */
    operator fun invoke(input: Input): Output

}