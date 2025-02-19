package org.acme;


public class NBody {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double deltaT = Double.parseDouble(args[1]);

        int n = StdIn.readInt();
        double radius = StdIn.readDouble();
        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] mass = new double[n];
        double G = 6.67e-11;



        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
        }

        for (double t = 0; t < T; t += deltaT) {
            for (int i = 0; i < n; i++) {
                double[] Fx = new double[n];
                double[] Fy = new double[n];
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        double dx = px[j] - px[i];
                        double dy = py[j] - py[i];
                        double r = Math.sqrt(dx * dx + dy * dy);
                        double F = (G * mass[i] * mass[j]) / (r * r);
                        Fx[i] += F * dx / r;
                        Fy[i] += F * dy / r;
                    }
                }

                // draw planet for px, py -> draw
                double ax = Fx[i] / mass[i];
                double ay = Fy[i] / mass[i];
                // calculate vx, vy -> velocities
                vx[i] += ax * deltaT;
                vy[i] += ay * deltaT;
                // calcuate px, py -> positions
                px[i] += vx[i] * deltaT;
                py[i] += vy[i] * deltaT;

            }
        }
    }
}
