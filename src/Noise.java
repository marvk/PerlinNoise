import java.util.Random;

public class Noise {

    /**
     * Generates a random noise array with range of 0-1
     *
     * @param width Width of the resulting array
     * @param height Height of the resulting array
     * @return 2D Array of random noise
     */
    public static double[][] generateRandomNoise(int width, int height) {
        Random random = new Random();
        double[][] noise = new double[width][height];

        //Fill array noise with random noise (range 0-1)
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = random.nextDouble() % 1;
            }
        }

        return noise;
    }

    /**
     * Generate a smoother noise based on an input noise
     *
     * @param baseNoise Base array of the generatd smooth noise
     * @param octave Octave of the period and frequency
     * @return 2D Array of smooth noise
     */
    public static double[][] generateSmoothNoise(double[][] baseNoise, int octave) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        double[][] smoothNoise = new double[width][height];

        int samplePeriod = 1 << octave;
        double sampleFrequency = 1.0f / samplePeriod;

        for (int i = 0; i < width; i++) {
            //Calculate horizontal sampling indices
            int sample_i0 = (i / samplePeriod) * samplePeriod;
            int sample_i1 = (sample_i0 + samplePeriod) % width;

            double horizontal_blend = (i - sample_i0) * sampleFrequency;

            for (int j = 0; j < height; j++) {
                //Calculate vertical sampling indices
                int sample_j0 = (j / samplePeriod) * samplePeriod;
                int sample_j1 = (sample_j0 + samplePeriod) % height;

                double vertical_blend = (j - sample_j0) * sampleFrequency;

                //Interpolate the top two corners
                double top = Interpolate(baseNoise[sample_i0][sample_j0], baseNoise[sample_i1][sample_j0], horizontal_blend);

                //Interpolate the bottom two corners
                double bottom = Interpolate(baseNoise[sample_i0][sample_j1], baseNoise[sample_i1][sample_j1], horizontal_blend);

                //Final interpolation
                smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);
            }
        }

        return smoothNoise;
    }

    /**
     * Generates random Perlin Noise based on an input noise
     *
     * @param baseNoise Base array of the generatd Perlin Noise
     * @param octaveCount Number of octaves
     * @return 2D Array of Perlin Noise
     */
    public static double[][] generatePerlinNoise(double[][] baseNoise, int octaveCount) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        //Array of 2D arrays of noise with their corresponding octave
        double[][][] smoothNoise = new double[octaveCount][][];


        double persistance = 0.5f;

        //Generate smooth noise
        for (int i = 0; i < octaveCount; i++) {
            smoothNoise[i] = generateSmoothNoise(baseNoise, i);
        }

        double[][] perlinNoise = new double[width][height];

        double amplitude = 1.0f;
        double totalAmplitude = 0.0f;

        //Blend the noise arrays together into the perlinNoise array
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistance;
            totalAmplitude += amplitude;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
                }
            }
        }

        //Normalisation
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                perlinNoise[i][j] /= totalAmplitude;

                //perlinNoise[i][j] = Math.min(1, perlinNoise[i][j]);
                //perlinNoise[i][j] = Math.max(0, perlinNoise[i][j]);
            }
        }

        return perlinNoise;
    }

    /**
     * Interpolates two values.
     *
     * @param x0 First value to interpolate
     * @param x1 Second value to interpolate
     * @param alpha Weight of x0 and x1 with range 0-1. The closer alpha is to 0
     *              the closer the result will be to 0 and vise versa.
     * @return Interpolated value
     */
    public static double Interpolate(double x0, double x1, double alpha)
    {
        return x0 * (1 - alpha) + alpha * x1;
    }
}
