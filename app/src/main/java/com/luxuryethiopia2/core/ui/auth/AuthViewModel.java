package com.luxuryethiopia2.core.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.luxuryethiopia2.core.data.repository.AccountVerificationRepository;
import com.luxuryethiopia2.core.domain.model.UserAccount;

/**
 * AuthViewModel exposes authentication UI state via lifecycle-aware LiveData.
 *
 * Usage example from an Activity or Fragment:
 *
 * <pre>
 * public class LoginActivity extends AppCompatActivity {
 *
 *     private AuthViewModel authViewModel;
 *
 *     protected void onCreate(Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *
 *         authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
 *
 *         // Observe auth state
 *         authViewModel.getAuthState().observe(this, state -> {
 *             switch (state.getStatus()) {
 *                 case LOADING:
 *                     findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
 *                     break;
 *                 case SUCCESS:
 *                     findViewById(R.id.progressBar).setVisibility(View.GONE);
 *                     Toast.makeText(this, "Welcome, " + state.getUser().getUsername(), Toast.LENGTH_SHORT).show();
 *                     startActivity(new Intent(this, DashboardActivity.class));
 *                     break;
 *                 case INVALID_CREDENTIALS:
 *                     findViewById(R.id.progressBar).setVisibility(View.GONE);
 *                     findViewById(R.id.errorText).setVisibility(View.VISIBLE);
 *                     ((TextView) findViewById(R.id.errorText)).setText(state.getMessage());
 *                     break;
 *                 case ERROR:
 *                     findViewById(R.id.progressBar).setVisibility(View.GONE);
 *                     Toast.makeText(this, state.getMessage(), Toast.LENGTH_LONG).show();
 *                     break;
 *             }
 *         });
 *
 *         // Observe verification status
 *         authViewModel.getVerificationStatus().observe(this, verified -> {
 *             if (verified) {
 *                 findViewById(R.id.verifiedBadge).setVisibility(View.VISIBLE);
 *             } else {
 *                 findViewById(R.id.verifiedBadge).setVisibility(View.GONE);
 *             }
 *         });
 *
 *         // Trigger login on button click
 *         findViewById(R.id.loginButton).setOnClickListener(v -> {
 *             String username = ((EditText) findViewById(R.id.usernameInput)).getText().toString();
 *             String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString();
 *             authViewModel.login(username, password);
 *         });
 *
 *         // Trigger registration
 *         findViewById(R.id.registerButton).setOnClickListener(v -> {
 *             String username = ((EditText) findViewById(R.id.regUsernameInput)).getText().toString();
 *             String email = ((EditText) findViewById(R.id.regEmailInput)).getText().toString();
 *             String password = ((EditText) findViewById(R.id.regPasswordInput)).getText().toString();
 *             authViewModel.register(username, email, password);
 *         });
 *     }
 * }
 * </pre>
 */
public class AuthViewModel extends AndroidViewModel {

    public enum AuthStatus {
        IDLE,
        LOADING,
        SUCCESS,
        ERROR,
        INVALID_CREDENTIALS
    }

    public static class AuthState {
        private final AuthStatus status;
        private final String message;
        private final UserAccount user;

        public AuthState(AuthStatus status, String message, UserAccount user) {
            this.status = status;
            this.message = message;
            this.user = user;
        }

        public AuthStatus getStatus() { return status; }
        public String getMessage() { return message; }
        public UserAccount getUser() { return user; }
    }

    private final AccountVerificationRepository repository;
    private final MediatorLiveData<AuthState> authState = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> verificationStatus = new MutableLiveData<>(false);

    public AuthViewModel(@NonNull Application application) {
        super(application);
        this.repository = new AccountVerificationRepository(application);
        authState.setValue(new AuthState(AuthStatus.IDLE, null, null));
    }

    public LiveData<AuthState> getAuthState() {
        return authState;
    }

    public LiveData<Boolean> getVerificationStatus() {
        return verificationStatus;
    }

    public void register(String username, String email, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            authState.setValue(new AuthState(AuthStatus.ERROR, "Username and password are required.", null));
            return;
        }

        authState.setValue(new AuthState(AuthStatus.LOADING, "Creating account...", null));

        LiveData<Long> result = repository.registerUser(username, email, password);
        authState.addSource(result, userId -> {
            authState.removeSource(result);
            if (userId != null && userId > 0) {
                authState.setValue(new AuthState(AuthStatus.SUCCESS,
                        "Account created successfully. Please log in.", null));
            } else {
                authState.setValue(new AuthState(AuthStatus.ERROR,
                        "Registration failed. Username or email may already be taken.", null));
            }
        });
    }

    public void login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            authState.setValue(new AuthState(AuthStatus.ERROR, "Username and password are required.", null));
            return;
        }

        authState.setValue(new AuthState(AuthStatus.LOADING, "Signing in...", null));

        LiveData<UserAccount> result = repository.authenticateUser(username, password);
        authState.addSource(result, user -> {
            authState.removeSource(result);
            if (user != null) {
                authState.setValue(new AuthState(AuthStatus.SUCCESS, "Welcome, " + user.getUsername(), user));
                checkVerification(user.getId());
            } else {
                authState.setValue(new AuthState(AuthStatus.INVALID_CREDENTIALS,
                        "Invalid username or password.", null));
            }
        });
    }

    public void checkVerification(int userId) {
        LiveData<Boolean> result = repository.checkVerificationStatus(userId);
        verificationStatus.addSource(result, verified -> {
            verificationStatus.removeSource(result);
            verificationStatus.setValue(verified != null && verified);
        });
    }

    public void verifyUser(int userId) {
        repository.verifyUser(userId);
        verificationStatus.setValue(true);
    }

    public void resetState() {
        authState.setValue(new AuthState(AuthStatus.IDLE, null, null));
    }
}
