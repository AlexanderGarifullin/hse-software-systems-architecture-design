package hse_paps_code.paps.transfers;

public record TaskPayload(
        String name,
        String statement,
        Integer timeLimit,
        Integer memoryLimit,
        String solve
) {
}
