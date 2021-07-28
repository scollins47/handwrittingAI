public class Neuron{
    private double value;
    private double[][]pixelated_image;
    private Neuron[]connectedNeurons;

    public Neuron(int value, double[][]pixelated_image,Neuron[]connectedNeurons){
        this.value = value;
        this.pixelated_image = pixelated_image;
        this.connectedNeurons = connectedNeurons;
    }

}