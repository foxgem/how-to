import os
import tensorflow as tf

# docker run -d -v $Prefix/model:/bitnami/model-data -P --expose 9000 --name tensorflow-serving bitnami/tensorflow-serving

# Train

x = tf.constant(1.0, name='input')
w = tf.Variable(0.8, name='weight')
y = tf.multiply(w, x, name='output')
y_ = tf.constant(0.0, name='correct_value')
loss = tf.pow(y - y_, 2, name='loss')
train_step = tf.train.GradientDescentOptimizer(0.025).minimize(loss)

for value in [x, w, y, y_, loss]:
    tf.summary.scalar(value.op.name, value)

summaries = tf.summary.merge_all()

sess = tf.Session()
summary_writer = tf.summary.FileWriter('log_simple_stats', sess.graph)

sess.run(tf.global_variables_initializer())

for i in range(100):
    summary_writer.add_summary(sess.run(summaries), i)
    sess.run(train_step)

print("Train is done, weight = ", sess.run(w))

# Export

version = 1
path = os.path.join('model', str(version))
builder = tf.saved_model.builder.SavedModelBuilder(path)

builder.add_meta_graph_and_variables(
    sess,
    [tf.saved_model.tag_constants.SERVING],
    signature_def_map={
        "helloTF": tf.saved_model.signature_def_utils.predict_signature_def(
            inputs={"x": x},
            outputs={"y": y}
        )
    }
)

builder.save()

sess.close()
