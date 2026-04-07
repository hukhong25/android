package vn.haui.a260330_studentsm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jspecify.annotations.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout tilName, tilDob, tilPhone, tilEmail;
    private TextInputEditText edtName, edtDob, edtPhone, edtEmail;
    private MaterialButton btnSubmit;

    // --- MỚI ---
    private RecyclerView rvStudents;
    private StudentAdapter studentAdapter;
    private ArrayList<StudentModel> lstStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 1. Ánh xạ View
        tilName = findViewById(R.id.til_name);
        tilDob = findViewById(R.id.til_dob);
        tilPhone = findViewById(R.id.til_phone);
        tilEmail = findViewById(R.id.til_email);

        edtName = findViewById(R.id.edt_name);
        edtDob = findViewById(R.id.edt_dob);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        btnSubmit = findViewById(R.id.btn_submit);

        // 2. --- MỚI --- Thiết lập RecyclerView
        rvStudents = findViewById(R.id.rv_students);
        lstStudents = new ArrayList<>();
        // (Khởi tạo Adapter)
        studentAdapter = new StudentAdapter(this, lstStudents, new StudentAdapter.OnclickItemStudentListener() {
            @Override
            public void onClickItemStudentListener(int position) {
                String details = "Thông tin chi tiết:\n" +
                        "Họ tên: " + lstStudents.get(position).getName() + "\n" +
                        "Ngày sinh: " + lstStudents.get(position).getDob() + "\n" +
                        "SĐT: " + lstStudents.get(position).getPhone() + "\n" +
                        "Email: " + lstStudents.get(position).getEmail();
                Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickAvatarStudentListener(int position) {
                Toast.makeText(MainActivity.this, "Bạn vừa chọn anh của sv sô " + (position + 1), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onClickImagButtonRemoveListener(int position) {
                // Xác nhận trước khi xóa
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Warnning!!!")
                        .setMessage("Are you sure to remove")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                lstStudents.remove(position);
                                studentAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

        rvStudents.setLayoutManager(new LinearLayoutManager(this));
        rvStudents.setAdapter(studentAdapter);

        // 2. Xử lý sự kiện chọn Ngày sinh với MaterialDatePicker
        edtDob.setOnClickListener(v -> showDatePickerDialog());

        // 3. Xử lý sự kiện bấm Nút Xác nhận (Được cập nhật)
        btnSubmit.setOnClickListener(v -> {
            if (validateData()) {
                // Thêm dữ liệu vào ArrayList và cập nhật RecyclerView
                String name = edtName.getText().toString().trim();
                String dob = edtDob.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();

                // Dùng ảnh mặc định của hệ thống
                lstStudents.add(new StudentModel(name, dob, phone, email, android.R.drawable.sym_def_app_icon));

                // --- Mẹo: Cập nhật mượt mà thay vì notifyDataSetChanged() ---
                studentAdapter.notifyItemInserted(lstStudents.size() - 1);

                // Xóa sạch form sau khi thêm
//                edtName.setText("");
//                edtDob.setText("");
//                edtPhone.setText("");
//                edtEmail.setText("");

                Toast.makeText(this, "Nhập thông tin thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // --- (Hàm validateData và showDatePickerDialog Giữ nguyên như prompt trước) ---
    private void showDatePickerDialog() {
        // Khởi tạo DatePicker chuẩn Material 3
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("CHỌN NGÀY SINH")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            // Định dạng ngày được chọn và hiển thị lên EditText
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dateString = sdf.format(new Date(selection));
            edtDob.setText(dateString);
            tilDob.setErrorEnabled(false); // Tắt báo lỗi nếu đã chọn
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private boolean validateData() {
        boolean isValid = true;

        String name = edtName.getText().toString().trim();
        String dob = edtDob.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        // Kiểm tra Họ tên
        if (name.isEmpty()) {
            tilName.setError("Vui lòng nhập họ tên");
            isValid = false;
        } else {
            tilName.setErrorEnabled(false);
        }

        // Kiểm tra Ngày sinh
        if (dob.isEmpty()) {
            tilDob.setError("Vui lòng chọn ngày sinh");
            isValid = false;
        } else {
            tilDob.setErrorEnabled(false);
        }

        // Kiểm tra Số điện thoại (Định dạng VN: bắt đầu bằng 0, có 10 số)
        String phoneRegex = "^0[0-9]{9}$";
        if (phone.isEmpty()) {
            tilPhone.setError("Vui lòng nhập số điện thoại");
            isValid = false;
        } else if (!phone.matches(phoneRegex)) {
            tilPhone.setError("Số điện thoại không hợp lệ (gồm 10 số, bắt đầu bằng 0)");
            isValid = false;
        } else {
            tilPhone.setErrorEnabled(false);
        }

        // Kiểm tra Email (Sử dụng Patterns có sẵn của Android)
        if (email.isEmpty()) {
            tilEmail.setError("Vui lòng nhập email");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Email sai định dạng (VD: ten@gmail.com)");
            isValid = false;
        } else {
            tilEmail.setErrorEnabled(false);
        }

        return isValid;
    }
}